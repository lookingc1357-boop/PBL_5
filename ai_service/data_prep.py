import pandas as pd


SELECTED_CWE = {"CWE-78", "CWE-89", "CWE-120", "CWE-787"}


def prepare_bigvul(input_csv: str, output_csv: str) -> None:
    data = pd.read_csv(input_csv)
    data = data[data["cwe"].isin(SELECTED_CWE)]
    data = data.dropna(subset=["code", "cwe"])
    data["code"] = data["code"].astype(str).str.slice(0, 12000)
    data.to_csv(output_csv, index=False)


if __name__ == "__main__":
    prepare_bigvul("bigvul.csv", "bigvul_filtered.csv")
