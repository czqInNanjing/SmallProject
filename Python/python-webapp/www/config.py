#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""Read and Deal with config file"""

__author__ = 'Qiang Chen'

import www.config_default as default

class Dict(dict):
    """Simple dict but support access as x.y style"""

    def __init__(self, names=(), values=(), **kw):
        super().__init__(**kw)
        for k, v in zip(names, values):
            self[k] = v

    def __getattr__(self, item):
        try:
            return self[item]
        except KeyError:
            raise AttributeError(" Dict Object has no attribute '%s'" % item)

    def __setattr__(self, key, value):
        self[key] = value


def merge(defaults, override):
    r = {}
    for k, v in defaults.items():
        if k in override:
            if isinstance(v, dict):
                r[k] = merge(v, override[k])
            else:
                r[k] = override[k]
        else:
            r[k] = v
    return r

def to_Dict(d):
    D = Dict()
    for k, v in d.items():
        D[k] = to_Dict(v) if isinstance(v, dict) else v
    return D

configs = default.configs

try:
    import www.config_override as override
    configs = merge(default.configs, override.configs)
except ImportError:
    pass

configs = to_Dict(configs)