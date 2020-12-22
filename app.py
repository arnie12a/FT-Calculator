import pandas as pd

a = pd.read_csv("FTpercentagePerSession.csv")

a.to_html("output.html")

html_file = a.to_html()