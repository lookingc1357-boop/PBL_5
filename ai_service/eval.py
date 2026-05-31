from sklearn.metrics import classification_report, f1_score


def evaluate(y_true, y_pred):
    return {
        "macro_f1": f1_score(y_true, y_pred, average="macro"),
        "report": classification_report(y_true, y_pred, zero_division=0),
    }
