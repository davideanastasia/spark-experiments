#!/usr/bin/python

import random
import uuid

for _ in range(1000000):
   id = str(uuid.uuid4())
   
   for _ in range(random.randint(1, 3)):
      for _ in range(random.randint(1, 10)):
         value = random.randint(1, 50)
         print '{str_id}\t{val}'.format(str_id=id, val=value)