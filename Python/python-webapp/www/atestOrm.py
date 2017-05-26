#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""Module Comment"""

__author__ = 'Qiang Chen'

import www.orm as orm
from www.models import User, Blog, Comment

import www.orm as orm
import asyncio
from www.models import User, Blog, Comment

async def test(aloop):

    await orm.create_pool(loop=aloop, user='www-data', password='www-data', database='awesome')
    u = User(name='jxk', email='jxk@examp3322le.com', passwd='0987654321', image='about:blank')
    await u.save()

loop = asyncio.get_event_loop()
loop.run_until_complete(test(loop))
