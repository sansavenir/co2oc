from sqlalchemy import Column, Integer, Unicode, UnicodeText, String
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
import glob
from sqlalchemy.ext.declarative import declarative_base
from random import choice
# from Item import Item
import Item
import pickle
import os
import numpy as np
from tqdm import tqdm

os.remove('full.db')
with open('tot/tot.dat', "rb") as f:
  data = pickle.load(f)
  # n = []
  # for i in a:
  #   n.append(Item(i.name, i.desc, i.gen_info, i.nutrients, i.origin, i.price, i.orig_price))
  # with open(file, "wb") as f:
  #   pickle.dump(n, f)


Item.Base.metadata.create_all()

Session = sessionmaker(bind=Item.engine)
s = Session()
for (k, r) in tqdm(enumerate(data), total=len(data)):
  r.mod_gen_info()
  r.mod_nutrients_info()
  r.mod_origin()
  r.mod_price()
  r.mod_orig_price()
  r.id = k
  r.mod_desc()
  r.mod_co2()
  r.mod_name()


  s.add(r)


s.commit()
