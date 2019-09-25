from functools import lru_cache as memoize
from random import randint

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


total = 0
for i in range(1, 10000):
    avg = 0
    for j in range(1, 7):
        avg += randint(1, 6)
    avg /= 6
    total += avg
print(total / 10000)


if 0:
    print('At least points prob:')
    for i in range(1, 11):
        for j in range(1, 61):
            print(str(i) + ' rolls, ' + str(j) + ' points | ' + str(roll_at_least(j, i)))

if 0:
    i = 10
    print('At least prob with', i, 'roll')
    for j in range(1, 61):
        print(roll_at_least(j, i))

if 0:
    print('Roll exact:')
    for j in range(1, 11):
        for i in range(1, 61):
            print(str(i) + ' rolls, ' + str(j) + ' points | ' + str(roll_dice(j, i)))

if 0:
    print('Exact points prob:')
    for i in range(1, 61):
        for j in range(1, 11):
            print(str(i) + ' points, ' + str(j) + ' rolls | ' + str(roll_at_least(i, j)))

