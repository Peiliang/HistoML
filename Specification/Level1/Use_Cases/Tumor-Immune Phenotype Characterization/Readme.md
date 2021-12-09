## Tumor-Immune Phenotype Characterization

This use case demonstrate how to calculate Tumor Infiltrating Lymphocytes (TIL) using HistoML.

#### Important Files

* Representation.xml: HistoML representation file of a tumor-immune phenotype.
* Stroma.rq and Lymphocytes.rq: Sparql queries for stroma components and lymphocytes.
* Annotation.db: Area annotation of the stroma and lymphocytes.
* CalculateTIL.java: Java code to execute Sparql queries and calculate TIL.
* Execute.py: Execute the use case.

#### How to Use

Install all requirements for python:

```shell
pip3 install -r requirements.txt
```

Run the use case:

```shell
python3 Execute.py
```

Java 11 runtime is required to run the queries.

