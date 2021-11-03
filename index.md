# Histopathology Markup Language (HistoML)



## Brief Introduction

Computational pathology provides accurate tools for defining the intra-tumoral ecological spatial context, having substantial implications for cancer research and medicine. However, the large-scale implementation of computational pathology has been hindered by the lack of a common information standard to represent various histopathological features. Here we propose Histopathology Markup Language (HistoML) Level 1 for comprehensively and precisely representing various histopathological features, including histopathological phenotypes, their individual components as well as diagnosing logics of pathologists at multiple levels of detail, in a machine-understandable format. We pilot HistoML in representing histopathological features of several neoplastic diseases and exemplify computational analyses on the representations. The representation files and the source code of these uses cases, as well as the ontology specification and documentation of HistoML, are available in this website.



## Links

HistoML [ontology specification](https://github.com/BioPAX/specification/tree/master/Level3/specification) and [documentation](https://github.com/BioPAX/specification/tree/master/Level3/docs)

Representation Examples

- [Rhabdoid Cells](https://github.com/Peiliang/HistoML/tree/master/Specification/Level1/Representation%20Examples/Rhabdoid%20cells%20in%20RCC%20(renal%20cell%20carcinoma))
- Alveolar Pattern
- Tumors Extending into Renal Sinus
- Subtyping Process

Use Cases

- Quantification of Tumor-immune phenotype
- Population-based comparison between nuclear morphologic parameters and ISUP grades
- Automatic Diagnosis and Evaluation

Histp



## "Hello World" example

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
	xmlns:owl="http://www.w3.org/2002/07/owl#" 
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 
    xmlns:histo="http://www.semanticweb.org/release/HistoML1.owl#"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema#">
	<owl:Ontology rdf:about="">
        <owl:imports rdf:resource="http://www.semanticweb.org/release/HistoML1.owl"/>
    </owl:Ontology>
	<histo:NeoplasticCell rdf:ID="Hello_World_Cell">
		<histo:displayName rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Hello World</histo:displayName>
	</histo:NeoplasticCell>
</rdf:RDF>
```



## HistoML Team

[Chen Li's group](http://www.chenli.group/home)

