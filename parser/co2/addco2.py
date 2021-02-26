import sys
sys.path.append('../')
import csv
import numpy as np
from google_trans_new import google_translator
import pickle

with open('data.csv') as csv_file:
  csv_reader = csv.reader(csv_file, delimiter=',')
  co2_data = list(csv_reader)[1:]

co2_data = np.asarray([np.asarray(a) for a in co2_data])
print(co2_data)
# names = co2_data[:, 0][1:]
#
# translator = google_translator()
# names = [a.replace('(', '').replace(')', '').replace(' &', '') for a in names]
# names = [translator.translate(a, lang_tgt='de').lower() for a in names]
# print(names)

# names = ['weizenroggenbrot ', 'maismehl ', 'gerstenbier ', 'haferflocken ', 'reis ', 'kartoffeln ',
#          'maniok ', 'rohrzucker ', 'zuckerrübe ', 'andere impulse ', 'erbsen ', 'nüsse ', 'erdnüsse ',
#          'soja milch ', 'tofu ', 'sojaöl ', 'palmöl ', 'sonnenblumenöl ', 'rapsöl ', 'olivenöl ', 'tomaten ',
#          'zwiebel lauch ', 'wurzelgemüse ', 'brassicas ', 'anderes gemüse ', 'zitrusfrucht ', 'bananen ',
#          'äpfel ', 'beeren trauben ', 'wein ', 'andere früchte ', 'kaffee ', 'dunkle schokolade ', 'rinderherde ',
#          'rindermilchherde ', 'lammhammel ', 'schweinefleisch ', 'geflügelfleisch ', 'milch ', 'käse ',
#          'eier ', 'fisch gezüchtet ', 'garnelen gezüchtet ']

names = [['weizenroggenbrot'], ['maismehl'], ['gersten'], ['haferflocken'], ['reis'], ['kartoffeln'], ['maniok'],
         ['rohrzucker'], ['zuckerrübe'], ['andere impulse'], ['erbsen'], ['nüsse'], ['erdnüsse'], ['soja drink'],
         ['tofu'], ['sojaöl'], ['palmöl'], ['sonnenblumenöl'], ['rapsöl'], ['olivenöl'], ['tomaten'],
         ['zwiebel', ' lauch'], ['wurzelgemüse'], ['brassicas'], ['gemüse'], ['zitrusfrucht'], ['bananen'],
         ['äpfel'], ['beeren', 'trauben'], [' wein '], ['andere früchte'], ['kaffee'], ['schokolade'],
         ['rind'], ['rindermilchherde'], ['lamm', 'hammel'], ['schwein'], ['geflügelfleisch', 'poulet'], ['milch'],
         ['käse'], ['eier'], ['fisch'], ['krevette']]

