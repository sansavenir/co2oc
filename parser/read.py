import numpy as np
import pickle
from Item import Item
import glob
import csv
import difflib
from fuzzywuzzy import process
from ngram import NGram



with open('receipts-details.csv') as csv_file:
  csv_reader = csv.reader(csv_file, delimiter=';')
  data = list(csv_reader)
  data = np.asarray(data)
  names = data[1:,5]


with open('tot/tot.dat', "rb") as f:
  data = pickle.load(f)

c = [1 if a.price is None else 0 for a in data]
print(sum(c))

# dataset_names = [r.name for r in res]
# n = NGram()
# for a in dataset_names:
#   n.add(a)
#
# for a in names:
#   # res = difflib.get_close_matches(a,dataset_names)
#   print(a,':', n.find(a, 0.5))
#   print('')







