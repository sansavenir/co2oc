from sqlalchemy import Column, Integer, Unicode, UnicodeText, String, Float
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
import re
import pickle
from google_trans_new import google_translator
import numpy as np
import time
from country_list import countries_for_language

countries_de = set(dict(countries_for_language('de')).values())
countries_fr = set(dict(countries_for_language('fr')).values())
countries_it = set(dict(countries_for_language('it')).values())
countries = (countries_it.union(countries_fr)).union(countries_de)

nut_names = ['Energie', 'Fett', 'davon gesättigte Fettsäuren', 'Kohlenhydrate', 'davon Zucker', 'Ballaststoffe', 'Eiweiss', 'Salz']


translator = google_translator()

months = ['January', 'February', 'March', 'April', 'May', 'June', 'July',
          'August', 'September', 'October', 'November', 'December']
months = [translator.translate(a, lang_tgt='de').lower().replace(' ','') for a in months]
months[4] = 'mai'

s_months = set([a.lower() for a in months])


engine = create_engine('sqlite://///Users/jakubkotal/Desktop/migdata/full.db')
Base = declarative_base(bind=engine)


class Item(Base):
  __tablename__ = 'items'
  id = Column(Integer, primary_key=True)
  name = Column(String, nullable=True)
  desc = Column(String, nullable=True)
  gen_info = Column(String, nullable=True)
  nutrients = Column(String, nullable=True)
  origin = Column(String, nullable=True)
  price = Column(Float, nullable=True)
  orig_price = Column(Float, nullable=True)
  link = Column(String, nullable=True)
  season = Column(String, nullable=True)
  weight = Column(Float, nullable=True)

  def __init__(self, name, desc, gen_info, nutrients, origin, price, orig_price, link):
    self.id = 0
    self.name = name
    self.desc = desc
    self.gen_info = gen_info
    self.nutrients = nutrients
    self.origin = origin
    self.price = price
    self.orig_price = orig_price
    self.link = link
    self.weight = -1.

  def __str__(self):
    print(self.name if self.name is not None else '')
    print(self.desc if self.desc is not None else '')
    print(self.gen_info if self.gen_info is not None else '')
    print(self.nutrients if self.nutrients is not None else '')
    print(self.origin if self.origin is not None else '')
    print(self.price if self.price is not None else '')
    print(self.orig_price if self.orig_price is not None else '')
    return 'a'

  def mod_gen_info(self):
    self.gen_info = None if self.gen_info == -1 else self.gen_info
    self.season = None
    if self.gen_info is None:
      return
    res = ''
    for a in self.gen_info:
      if a[0] == 'Herkunft':
        res += a[1]
      if a[0] == 'Produktionsverfahren':
        res += a[1]
      if a[0] == 'Saison':
        self._prepare_seasons(a[1])
    self.gen_info = res if res != '' else None

  def mod_nutrients_info(self):
    if self.nutrients is not None:
      names = [a[0] for a in self.nutrients]
      res = np.zeros(len(nut_names))
      for n in nut_names:
        if n not in names:
          res[nut_names.index(n)] = 0.
        elif n == 'Energie':
          t = re.findall(r"[-+]?\d*\.\d+|\d+", self.nutrients[names.index(n)][1])
          res[nut_names.index(n)] = t[1] if len(t) > 1 else t[0]
        else:
          if self.nutrients[names.index(n)][1] == '':
            self.nutrients[names.index(n)][1] = '0.'
          res[nut_names.index(n)] = re.findall(r"[-+]?\d*\.\d+|\d+", self.nutrients[names.index(n)][1])[0]
      self.nutrients = ','.join(str(a) for a in res)

  def mod_origin(self):
    if self.origin is not None:
      c = self.origin[0][1].split(' ')
      self.origin = countries.intersection(set(c))
      if len(self.origin) == 0:
        self.origin = None
      else:
        self.origin = ' '.join(self.origin)

  def mod_price(self):
    if self.price is not None:
      self.price = re.findall(r"[-+]?\d*\.\d+|\d+", self.price)[0]

  def mod_orig_price(self):
    if self.orig_price is not None:
      self.orig_price = re.findall(r"[-+]?\d*\.\d+|\d+", self.orig_price)[0]

  def _prepare_seasons(self, text):
    # translated = translator.translate(text.lower()).split(' ')
    text = text.lower().replace('-', ' -').replace('  ', ' ').split(' ')
    # if len(s_months.intersection(set(text))) > 0:

    if text[0] == 'herbst':
      text = ['september','-','dezember']
    if text[0] == 'ganzjährig':
      self.season = ' '.join(str(e) for e in list(range(0,12)))
      return
    if len(text) == 1:
      self.season = months.index(text[0])
      return
    a = months.index(text[0])
    b = months.index(text[2])
    if a <= b:
      self.season = list(range(a, b+1))
    else:
      self.season = list(range(a, 12))
      self.season.extend(list(range(0, b+1)))
    self.season = " {} ".format(' '.join(str(e) for e in self.season))

  def mod_desc(self):
    self.weight = self.desc
    if self.weight is not None:
      if self.weight[:7] == 'Angebot':
        self.weight = '-1.'
      else:
        self.weight = self.weight.split(',')[0]
      multiplier = 1.
      if 'x' in self.weight:
        self.weight = self.weight.split('x')
        multiplier = float(re.findall(r"[-+]?\d*\.\d+|\d+", self.weight[0])[0])
        self.weight = self.weight[1]

      n = re.findall(r"[-+]?\d*\.\d+|\d+", self.weight)
      if 'kg' in self.weight:
        self.weight = 1000*float(n[0] if len(n) > 0 else 0)
      elif 'ml' in self.weight:
        self.weight = float(n[0] if len(n) > 0 else 0)
      elif 'cl' in self.weight:
        self.weight = 10*float(n[0] if len(n) > 0 else 0)
      elif 'dl' in self.weight:
        self.weight = 100*float(n[0] if len(n) > 0 else 0)
      elif 'l' in self.weight or 'L' in self.weight:
        self.weight = 1000*float(n[0] if len(n) > 0 else 0)
      elif 'g' in self.weight:
        self.weight = float(n[0] if len(n) > 0 else 0)
      else:
        self.weight = float(n[0] if len(n) > 0 else 0)
      self.weight *= multiplier





  def mod_name(self):
    self.name = self.name.lower()


