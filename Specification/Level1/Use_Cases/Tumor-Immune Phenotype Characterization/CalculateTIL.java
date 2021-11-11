import org.apache.jena.atlas.io.IndentedWriter;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileUtils;

import java.io.IOException;
import java.util.*;

public class Search {
    public static Model model = ModelFactory.createDefaultModel();
    public static String histoPrefix = "http://www.semanticweb.org/release/HistoML1.owl";
    private static final boolean debug = false;

    public static void ratio() throws IOException {
        histoPrefix = "https://histoml-1308125782.cos.ap-chengdu.myqcloud.com/HistoML.owl";
        Map<Integer, Integer> cellAreas = new HashMap<>();

        model.read(RDFDataMgr.open("Tumor_Immune_Phenotype.xml"), null);
        if (debug) print(model);

        for (String line : FileUtils.readWholeFileAsUTF8("Area.csv").split("\n")) {
            String[] line_arg = line.strip().split(",");
            cellAreas.put(Integer.parseInt(line_arg[0]), Integer.parseInt(line_arg[1]));
        }

        List<RDFNode> stromaData = execSingleQuery("stroma", FileUtils.readWholeFileAsUTF8("Stroma.rq"));
        int segmentation = Integer.parseInt(stromaData.get(1).toString());
        if (debug) {
            print(stromaData.get(0));
            System.out.printf("segmentation id: %d, area: %d\n", segmentation, cellAreas.get(segmentation));
        }

        int lymphocyteArea = 0;
        for (List<RDFNode> data : execQuery("lymphocytes", FileUtils.readWholeFileAsUTF8("Lymphocytes.rq"))) {
            int area = cellAreas.get(Integer.parseInt(data.get(1).toString()));
            lymphocyteArea += area;
            if (debug) {
                print(data.get(0));
                System.out.printf("segmentation id: %s, area: %d\n", data.get(1), area);
            }
        }

        System.out.printf("tumor infiltrating lymphocytes: %f%%", 100 * (double) lymphocyteArea / cellAreas.get(segmentation));
    }

    public static void main(String[] args) throws Exception {
        ratio();
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
