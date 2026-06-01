from pathlib import Path

for path in Path("src/main/resources/templates").rglob("*.html"):
    text = path.read_text(encoding="utf-8")
    text = text.replace('th:replace="/common_', 'th:replace="common_')
    path.write_text(text, encoding="utf-8", newline="")
