import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Search {
    public static Model model = ModelFactory.createDefaultModel();
    public static String histoPrefix = "https://histoml-1308125782.cos.ap-chengdu.myqcloud.com/HistoML.owl";
    private static final boolean debug = false;

    public static void transform(String[] args) throws IOException, TemplateException {
        histoPrefix = "https://histoml-1308125782.cos.ap-chengdu.myqcloud.com/HistoML.owl";
        class CellProp {
            public final int grade, area;
            public final double major_axis, circularity, entropy;
            public CellProp(ArrayNode node) {
                grade = node.get(0).asInt();
                area = node.get(1).asInt();
                major_axis = node.get(2).asDouble();
                circularity = node.get(3).asDouble();
                entropy = node.get(4).asDouble();
            }
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDirectoryForTemplateLoading(new File("."));
        Template template = cfg.getTemplate("Template.xml");

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode root = (ArrayNode) objectMapper.readTree(new File("CellData.json"));
        List<CellProp> cellProps = new ArrayList<>();
        List<Integer> regions = new ArrayList<>();
        Map<String, Object> dataModel = new HashMap<>();
        for (Iterator<JsonNode> iter = root.elements(); iter.hasNext(); ) {
            ArrayNode array = (ArrayNode) iter.next();
            CellProp prop = new CellProp(array);
            cellProps.add(prop);
            regions.add(prop.grade);
        }

        dataModel.put("id", "Slide");
        dataModel.put("regions", regions);
        template.process(dataModel, new FileWriter("Representation.xml"));
    }

    public static void main(String[] args) throws Exception {
        transform(args);
    }

    public static List<List<RDFNode>> execQuery(String subject, String queryString, Object ... binding) {
        queryString = "PREFIX histo: <" + histoPrefix + "#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + queryString;

        if (debug) System.out.printf("query %s:\n", subject);
        Query query = QueryFactory.create(queryString);
        if (debug) query.serialize(new IndentedWriter(System.out, true));

        QuerySolutionMap initialBinding = new QuerySolutionMap();
        for (int i = 0; i < binding.length; i += 2)
            initialBinding.add((String) binding[i], (RDFNode) binding[i + 1]);

        List<List<RDFNode>> result = new ArrayList<>();
        try (QueryExecution exec = QueryExecutionFactory.create(query, model, initialBinding)) {
            ResultSet rs = exec.execSelect();

            while (rs.hasNext()) {
                QuerySolution rb = rs.nextSolution();

                Iterator<String> names = rb.varNames();
                List<RDFNode> line = new ArrayList<>();
                while (names.hasNext()) {
                    RDFNode node = rb.get(names.next());
                    line.add(node);
                    if (debug) print(node);
                }

                if (debug) System.out.println();
                result.add(line);
            }

            if (debug) System.out.printf("%s count: %d\n\n", subject, result.size());
        }

        return result;
    }

    public static List<RDFNode> execSingleQuery(String subject, String queryString, Object ... binding) {
        queryString = "PREFIX histo: <" + histoPrefix + "#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + queryString;

        if (debug) System.out.printf("query %s:\n", subject);
        Query query = QueryFactory.create(queryString);
        if (debug) query.serialize(new IndentedWriter(System.out, true));

        QuerySolutionMap initialBinding = new QuerySolutionMap();
        for (int i = 0; i < binding.length; i += 2)
            initialBinding.add((String) binding[i], (RDFNode) binding[i + 1]);

        List<RDFNode> result = new ArrayList<>();
        try (QueryExecution exec = QueryExecutionFactory.create(query, model, initialBinding)) {
            ResultSet rs = exec.execSelect();

            while (rs.hasNext()) {
                QuerySolution rb = rs.nextSolution();

                Iterator<String> names = rb.varNames();
                while (names.hasNext()) {
                    RDFNode node = rb.get(names.next());
                    result.add(node);
                    if (debug) print(node);
                }
            }

            if (debug) System.out.printf("\n%s count: %d\n\n", subject, result.size());
        }

        return result;
    }

    public static void print(Model model) {
        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object);
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
        }
    }

    public static void print(RDFNode node) {
        String name = node.toString();
        int index = name.indexOf('#');
        if (index >= 0)
            name = name.substring(index + 1);
        System.out.printf("%s ", name);
    }

    public static String join(String ... args) {
        return String.join(System.getProperty("line.separator"), args);
    }
}
