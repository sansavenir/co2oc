from bs4 import BeautifulSoup
import time
from numpy import unicode
from selenium import webdriver
import pickle
from tqdm import tqdm
from Item import Item
import numpy as np


with open('tot/tot.dat', "rb") as f:
  data = (pickle.load(f))

def find_article_exact(link, driver):
  driver.get(link)
  time.sleep(1.)
  html = driver.page_source
  soup = BeautifulSoup(html, 'html.parser')

  gen_info = soup.find_all("div", {'id': 'my-panel-generalInformation'})
  nutrients = soup.find_all("table", {'id': 'nutrient-table'})
  origin = soup.find_all("div", {'id': 'my-panel-origins'})

  gen_info = makelist(gen_info[0]) if len(gen_info) > 0 else -1
  nutrients = makelist(nutrients[0]) if len(nutrients) > 0 else None
  origin = makelist(origin[0]) if len(origin) > 0 else None
  return gen_info, nutrients, origin

def makelist(table):
  soup = BeautifulSoup(str(table), 'html.parser')
  soup.findAll('table')
  result = []
  allrows = table.findAll('tr')
  for row in allrows:
    result.append([])
    allcols = row.findAll('td')
    for col in allcols:
      thestrings = [unicode(s) for s in col.findAll(text=True)]
      thetext = ''.join(thestrings)
      thetext = thetext.strip().replace('\n', '')
      result[-1].append(thetext)
  return [r for r in result if len(r) > 0]

driver = webdriver.Safari()
for id in tqdm(range(len(data))):
  item = data[id]
  if item.gen_info is None and item.nutrients is None and item.origin is None:
    try:
      gi, nut, orig = find_article_exact(item.link, driver)
      item.gen_info = gi
      item.nutrients = nut
      item.origin = orig
      data[id] = item
    except KeyboardInterrupt:
      with open('tot/tot.dat', "wb") as f:
        pickle.dump(data, f)
      break
    except:
      break
    # except:
    #   print('error')
    #   time.sleep(1.)
    #   continue
  if id % 100 == 0:
    with open('tot/tot.dat', "wb") as f:
      pickle.dump(data, f)

with open('tot/tot.dat', "wb") as f:
  pickle.dump(data, f)

