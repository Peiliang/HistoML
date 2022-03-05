## Standardization of Computational Models

This use case demonstrates how to translate prediction results of computational models into HistoML. We use a nuclei segmentation and grading model as an example.

#### Important Files

Template.xml: Template to translate the prediction results into HistoML.

Generate.java: Source code of running the nuclei segmentation and grading model and translating its predictions into HistoML representations.

Execute.py: Execute the use case.

Example.png: An example input of the nuclei segmentation and grading model.

Representation.xml: HistoML representation translated from the prediction of the model processing Example.png.

#### How to Use

Install all requirements for python:

```shell
pip3 install -r requirements.txt
```

Download the nuclei segmentation and grading model from [here](https://drive.google.com/file/d/11S064aegdil-CI-SdYk0DHp2n7d2Nv-8/view?usp=sharing) and extract all files into micronet folder.

Run the use case:

```shell
python3 Execute.py Example.png
```


CUDA environment and Java 11 runtime is required.


