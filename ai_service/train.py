from collections import Counter


def class_weights(labels):
    total = len(labels)
    counts = Counter(labels)
    return {label: total / (len(counts) * count) for label, count in counts.items()}


def train_mock(labels):
    weights = class_weights(labels)
    return {"status": "trained", "strategy": "inverse_frequency", "weights": weights}


if __name__ == "__main__":
    print(train_mock(["CWE-120", "CWE-120", "CWE-89", "CWE-78"]))
