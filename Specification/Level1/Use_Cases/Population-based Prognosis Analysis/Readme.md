## Population-based Prognosis Analysis

This use case demonstrates how to analyze large scale statistic data with HistoML.

#### Important Files

Template.xml: Template to generate HistoML representation files.

Grade1~3.rq & Endothelial.rq: Sparql queries for 4 types of cells.

Analysis.java: Source code of generating HistoML representations and run Sparql queries.

Execute.py: Execute the use case.

Representation folder: HistoML representations in this use case.

#### How to Use

In this use cases, HistoML representation files in "Representation" folder are pre-generated from the CCRCC Nuclei Grading Dataset, which can be downloaded from [here](https://dataset.chenli.group/home/ccrcc-grading). This dataset contains 1,000 512x512 images for CCRCC Nuclei Grading and Segmentation tasks.

Run the use case:

```shell
java -jar Analysis.jar area diameter circularity entropy
```

Area, diameter, circularity and entropy are 4 parameters calculated for each cell.

Java 11 runtime is required to run the queries.

#### Example Output

> average area:
> grade 1 (45120): 346.531272
> grade 2 (6407): 566.327923
> grade 3 (2780): 809.073741
> endothelial (16691): 293.694566

This means that the average nucleolus area of all 45120 grade 1 cells is 346.5 pixels, etc.

