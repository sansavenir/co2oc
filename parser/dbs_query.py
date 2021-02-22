import sqlite3
import csv
import numpy as np

db = sqlite3.connect('full.db')
c = db.cursor()

with open('receipts-details.csv') as csv_file:
  csv_reader = csv.reader(csv_file, delimiter=';')
  data = list(csv_reader)
  data = np.asarray(data)
  names = data[1:,[5,8]]
for n in names:
  p = n[1]
  name = n[0]
  n = n[0].replace('MClass ','').split(' ')
  n = ' AND '.join(['name Like "%{}%"'.format(a) for a in n])

  c.execute('SELECT name, id FROM items WHERE ({}) and (price == {} or orig_price == {} or price is NULL)'.format(n,p,p))
  # c.execute('SELECT name FROM items JOIN items ON ')
  res = c.fetchall()
  print(name, res)
  print('')