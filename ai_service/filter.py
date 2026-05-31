import re


def normalize_code(code: str) -> str:
    code = re.sub(r"/\*.*?\*/", "", code, flags=re.S)
    code = re.sub(r"//.*", "", code)
    code = "\n".join(line.rstrip() for line in code.splitlines())
    return code.strip()
