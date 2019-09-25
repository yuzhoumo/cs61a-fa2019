from functools import lru_cache as memoize


def six_sided(outcome):
    if 1 <= outcome <= 6:
        return 1/6
    else:
        return 0


dice = six_sided


@memoize(maxsize=None)
def roll_a_one(n):
    if n == 0:
        return 0
    return dice(1) + (1-dice(1)) * roll_a_one(n-1)


@memoize(maxsize=None)
def roll_no_ones(total, n):
    if total == 0 and n == 0:
        return 1
    elif n == 0:
        return 0
    else:
        chance, outcome = 0, 2
        while outcome <= 6:
            chance += dice(outcome) * roll_no_ones(total-outcome, n-1)
            outcome += 1
        return chance


def roll_dice(total, n):
    if total == 1:
        return roll_a_one(n)
    else:
        return roll_no_ones(total, n)


def roll_at_least(k, n):
    """P(At least K points with N dice)"""
    total, chance = k, 0
    while total <= 6 * n:
        chance += roll_dice(total, n)
        total += 1
    return chance

"""

# Nim strategies
goal = 21


def constant(k):
    return lambda n: k


@memoize(maxsize=None)
def winner(n, s, other_s):
    if n >= goal:
        return 0
    else:
        return 1 - winner(n + s(n), other_s, s)


@memoize(maxsize=None)
def optimal(n, other_s):
    future_s = lambda lambda_n: optimal(future_n, other_s)
    choice = 1
    while choice <= 3:
        if winner(n+choice, other_s, future_s) == 1:
            return choice
        choice += 1
    return 1 # Give up


def print_perfect():
    n = 0
    perfect = lambda n: optimal(n, perfect)
    while n < goal:
        if winner(n, perfect, perfect) == 0:
            print('Perfect play from', n, 'wins with choice', perfect(n))
        else:
            print('Perfect play from', n, 'looks bad... not gonna win')
        n += 1
"""
