
from Item import Item
from tqdm import tqdm
import glob
import pickle


# item = Item.Itemb(None, None, None, None, None, None, None, None)
dirs = glob.glob("new/*")
new = []
for file in dirs:
  with open(file, "rb") as f:
    new.extend(pickle.load(f))
#
dirs = glob.glob("lebensmittel/*")
old = []
for file in dirs:
  with open(file, "rb") as f:
    old.extend(pickle.load(f))

for o in tqdm(old):
  for n in new:
    if o.name == n.name:
      n.desc = o.desc
      n.gen_info = o.gen_info
      n.nutrients = o.nutrients
      n.origin = o.origin
      n.price = o.price
      n.orig_price = o.orig_price


with open("tot/tot.dat", "wb") as f:
  pickle.dump(new, f)