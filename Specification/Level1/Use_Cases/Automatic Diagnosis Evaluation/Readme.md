## Automatic Diagnosis Evaluation

This use case demonstrates how to automatically evaluate diagnosis with HistoML.

#### Important Files

HistoML.owl: Ontology for HistoML.

Histopathology Ontology Simplified.owl: Histopathology ontology for disease diagnosis.

Evaluation.java: Source code of automatic diagnosis evaluation.

Automatic Evaluation Intestinal Adenocarcinoma Correct.xml & Automatic Evaluation Intestinal Adenocarcinoma Wrong.xml: Diagnosis examples.

#### How to Use

Run the use case:

```shell
java -jar Evaluation.jar "Automatic Evaluation Intestinal Adenocarcinoma Correct.xml"
```

Java 11 runtime is required to run the queries.

#### Output Format

Below is the example output for "Automatic Evaluation Intestinal Adenocarcinoma Wrong.xml":

> Diagnosis1 is Cervical Adenocarcinoma : true
> Diagnosis1 is Human Papillomavirus-Related Endocervical Adenocarcinoma
>
> - identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
> - definition: Hallmarks of HPV-associated endocervical adenocarcinoma architecture include apical mitoses and karyorrhexis, conspicuous and identifiable at low-power magnification.
> - evidences: Glandular_Pattern5 Glandular_Pattern4

> Diagnosis2 is Human Papillomavirus-Related Endocervical Adenocarcinoma : true
> Diagnosis2 is Human Papillomavirus-Related Endocervical Adenocarcinoma
>
> - identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
> - definition: Hallmarks of HPV-associated endocervical adenocarcinoma architecture include apical mitoses and karyorrhexis, conspicuous and identifiable at low-power magnification.
> - evidences: Glandular_Pattern5 Glandular_Pattern4

> Diagnosis3 is Adenocarcinoma, HPV-associated, intestinal type : false
> Diagnosis3 is Human Papillomavirus-Related Endocervical Adenocarcinoma
>
> - identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
> - definition: Hallmarks of HPV-associated endocervical adenocarcinoma architecture include apical mitoses and karyorrhexis, conspicuous and identifiable at low-power magnification.
> - evidences: Glandular_Pattern5 Glandular_Pattern4
>
> Diagnosis3 is Adenocarcinoma, HPV-associated, mucinous type
>
> - identifier: 9283245040, title: Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition
>
> - definition: This type accounts for ~10% of all endocervical adenocarcinoma. There is intracytoplasmic mucin in  ≥ 50% of cells, typically with a minor component of usual adenocarcinoma.
> - evidences: Quantitative_Metric2

The output indicates that there are 3 diagnoses for this case, i.e. Diagnosis1~3.

Diagnosis1 states that this case is Cervical Adenocarcinoma, which is evaluated as true, through the guideline provided by Female Genital Tumours: WHO Classification of Tumours (Medicine) 5th Edition. There are two evidences that supports this claim: Glandular_Pattern5 and Glandular_Pattern4.

Similar to Diagnosis1, Diagnosis2 states that this case is Human Papillomavirus-Related Endocervical Adenocarcinoma, which is evaluated as true.

Diagnosis3 states that this case is Adenocarcinoma, HPV-associated, intestinal type, which is evaluated as false, because given evidences are not enough to yield this result. Instead, this case is shown to be Adenocarcinoma, HPV-associated, mucinous type, with supporting evidences Glandular_Pattern5, Glandular_Pattern4 and Quantitative_Metric2.
