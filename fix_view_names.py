from pathlib import Path

for path in Path("src/main/java").rglob("*.java"):
    text = path.read_text(encoding="utf-8")
    text = text.replace('setViewName("/', 'setViewName("')
    path.write_text(text, encoding="utf-8", newline="")
