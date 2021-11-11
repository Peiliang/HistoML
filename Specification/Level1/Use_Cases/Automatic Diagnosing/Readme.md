## Automatic Diagnosis Evaluation

This use case demonstrates how to automatically give diagnosis with HistoML.

#### Important Files

HistoML.owl: Ontology for HistoML.

Histopathology Ontology Simplified.owl: Histopathology ontology for disease diagnosis.

Diagnosing.java: Source code of automatic diagnosis.

Automatic Subtyping.xml: Diagnosis examples.

Automatic Subtyping Diagnosis.xml: Generated diagnosis result.

#### How to Use

Run the use case:

```shell
java -jar Diagnosing.jar "Automatic Subtyping.xml"
```

Java 11 runtime is required to run the queries.

#### Example Output

Below is the example output for "Automatic Subtyping.xml":

> Diagnosis is Human Papillomavirus-Related Endocervical Adenocarcinoma
>
> * identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
> * definition: Hallmarks of HPV-associated endocervical adenocarcinoma architecture include apical mitoses and karyorrhexis, conspicuous and identifiable at low-power magnification.
> * evidences: Glandular_Pattern5 Glandular_Pattern4
>
> Diagnosis is Adenocarcinoma, HPV-associated, usual type
>
> * identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
> * definition: Cells with mucinous cytoplasm constitute only 0-50% of the tumour.
> * evidences: Quantitative_Metric1

The output indicates that this case is Adenocarcinoma, HPV-associated, usual type, through the guideline provided by Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition, with supporting evidences Glandular_Pattern5, Glandular_Pattern4 and Quantitative_Metric1.

