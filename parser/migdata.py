import requests
from bs4 import BeautifulSoup
import time
from numpy import unicode
from selenium import webdriver
import pickle
from tqdm import tqdm
from Item import Item
import numpy as np


def find_article_exact(link, driver):
  driver.get(link)
  time.sleep(1.)
  html = driver.page_source
  soup = BeautifulSoup(html, 'html.parser')

  gen_info = soup.find_all("div", {'id': 'my-panel-generalInformation'})
  nutrients = soup.find_all("table", {'id': 'nutrient-table'})
  origin = soup.find_all("div", {'id': 'my-panel-origins'})

  gen_info = makelist(gen_info[0]) if len(gen_info) > 0 else None
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


def parse_mig(driver):
  res = []
  failed = []
  for page_id in tqdm(range(116, 216)):
    driver.get('https://produkte.migros.ch/sortiment/supermarkt/lebensmittel?page={}'.format(page_id))
    time.sleep(0.5)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    mydivs = soup.find_all("div", class_="sc-pjSSY sc-qZtCU cWPDKu")
    for div in mydivs:
      div_soup = BeautifulSoup(str(div), 'html.parser')
      orig_price = div_soup.find_all('span', {'data-testid': 'msrc-articles--article-original-price'})
      orig_price = orig_price[0].text if len(orig_price) > 0 else None

      price = div_soup.find_all('span', {'data-testid': 'msrc-articles--article-price'})
      price = price[0].text if len(price) > 0 else None

      name = div_soup.find_all('h2', {'data-testid': 'msrc-articles--article-name'})[0].text
      # name = name[0].text if len(name) > 0 else None

      desc = div_soup.find_all('p', {'data-testid': 'msrc-articles--article-description'})
      desc = desc[0].text if len(desc) > 0 else None

      link = div_soup.find_all('a', {'data-testid': 'msrc-articles--article-link'})[0]['href']
      # gen_info, nutrients, origin = find_article_exact(link, driver)
      gen_info, nutrients, origin = None, None, None
      res.append(Item(name, desc, gen_info, nutrients, origin, price, orig_price, link))
    # except:
    #   failed.append(page_id)
    #   np.save('failed.txt', np.asarray(failed))


    if page_id % 5 == 0:
      with open("new/pickle_{}.dat".format(page_id), "wb") as f:
        pickle.dump(res, f)
      res = []

# 10282

driver = webdriver.Safari()
parse_mig(driver)
