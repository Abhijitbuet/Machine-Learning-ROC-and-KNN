import numpy as numpy_library
import matplotlib.pyplot as lineGraph_library

def plot_roc(actual_labels, predicted_probabilities):
    data_size = len(actual_labels)
    ROC_Points = []
    tpr_values = []
    fpr_values = []
    for i in range(0,data_size):
        newPoint = ROC_Point(actual_labels[i], predicted_probabilities[i])
        ROC_Points.append(newPoint)

    ROC_Points = sorted(ROC_Points, key= lambda x : x.predicted_probability)
    for roc_point in ROC_Points:
        r = get_tpr_fpr(ROC_Points, roc_point.predicted_probability)
        tpr_values.append(r[0])
        fpr_values.append(r[1])

    lineGraph_library.plot(fpr_values, tpr_values)
    lineGraph_library.ylim([0, 1])
    lineGraph_library.xlim([0, 1])
    lineGraph_library.xticks(numpy_library.arange(0, 1.1, 0.1))
    lineGraph_library.yticks(numpy_library.arange(0, 1.1, 0.1))
    lineGraph_library.title("ROC Curve")
    lineGraph_library.xlabel('False Positive Rate')
    lineGraph_library.ylabel('True Positive Rate')
    lineGraph_library.show()


def get_tpr_fpr(ROC_Points, threshold):
    true_positive = 0
    false_positive = 0
    true_negative = 0
    false_negative = 0

    for roc_point in ROC_Points:
        if (roc_point.predicted_probability>=threshold) and roc_point.actual_label == 1:
            true_positive += 1.0
        elif (roc_point.predicted_probability < threshold) and roc_point.actual_label == 1:
            false_negative += 1.0
        elif (roc_point.predicted_probability >= threshold) and roc_point.actual_label == 0:
            false_positive += 1.0
        else:
            true_negative += 1.0

    tpr = true_positive*1.0 / (true_positive + false_negative)
    fpr = false_positive*1.0 / (false_positive + true_negative)

    return tpr, fpr

class ROC_Point:
  actual_label = 0
  predicted_probability = 0.5
  def __init__(self, actual_label, predicted_probability):
    self.actual_label = actual_label
    self.predicted_probability = predicted_probability


plot_roc([0, 0, 1, 0, 1, 0,0, 1, 1, 0, 1, 1, 1, 1, 0,1,1,1], [ .03, 0.08, .1, .11, .22, .32, .35, 0.42, 0.44, 0.48, 0.56, .65, .71, .72, .73, .8, .82, .99])
