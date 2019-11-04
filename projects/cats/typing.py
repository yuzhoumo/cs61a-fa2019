"""Typing test implementation"""

from utils import *
from ucb import main, interact, trace
from datetime import datetime


def choose(paragraphs, select, k):
    """
    Return the Kth paragraph from PARAGRAPHS for which SELECT called on the
    paragraph returns true. If there are fewer than K such paragraphs, return
    the empty string.
    """
    # BEGIN PROBLEM 1
    paragraphs = [option for option in paragraphs if select(option)]

    if -1 < k < len(paragraphs):
        return paragraphs[k]
    return ''
    # END PROBLEM 1


def about(topic):
    """
    Return a select function that returns whether a paragraph contains one
    of the words in TOPIC.

    >>> about_dogs = about(['dog', 'dogs', 'pup', 'puppy'])
    >>> choose(['Cute Dog!', 'That is a cat.', 'Nice pup!'], about_dogs, 0)
    'Cute Dog!'
    >>> choose(['Cute Dog!', 'That is a cat.', 'Nice pup.'], about_dogs, 1)
    'Nice pup.'
    """
    assert all([lower(x) == x for x in topic]), 'topics should be lowercase.'

    # BEGIN PROBLEM 2
    def select(option):
        option = [remove_punctuation(text) for text in split(lower(option))]

        for w in topic:
            if w in option:
                return True
        return False

    return select
    # END PROBLEM 2


def accuracy(typed, reference):
    """
    Return the accuracy (percentage of words typed correctly) of TYPED
    when compared to the prefix of REFERENCE that was typed.

    >>> accuracy('Cute Dog!', 'Cute Dog.')
    50.0
    >>> accuracy('A Cute Dog!', 'Cute Dog.')
    0.0
    >>> accuracy('cute Dog.', 'Cute Dog.')
    50.0
    >>> accuracy('Cute Dog. I say!', 'Cute Dog.')
    50.0
    >>> accuracy('Cute', 'Cute Dog.')
    100.0
    >>> accuracy('', 'Cute Dog.')
    0.0
    """
    typed_words = split(typed)
    reference_words = split(reference)
    # BEGIN PROBLEM 3
    correct_words, max_index = 0, min(len(typed_words), len(reference_words))

    if max_index == 0:
        return 0.0

    for i in range(max_index):
        if typed_words[i] == reference_words[i]:
            correct_words += 1

    return correct_words / len(typed_words) * 100
    # END PROBLEM 3


def wpm(typed, elapsed):
    """Return the words-per-minute (WPM) of the TYPED string."""
    assert elapsed > 0, 'Elapsed time must be positive'
    # BEGIN PROBLEM 4
    return 60 * len(typed) / (5 * elapsed)
    # END PROBLEM 4


def autocorrect(user_word, valid_words, diff_function, limit):
    """
    Returns the element of VALID_WORDS that has the smallest difference
    from USER_WORD. Instead returns USER_WORD if that difference is greater
    than or equal to LIMIT.
    """
    # BEGIN PROBLEM 5
    if user_word in valid_words:
        return user_word

    # Constructs dict with diff value keys, returns word from valid_words
    # Loops in reverse to overwrite undesired values
    diff_dict = {}
    for w in reversed(valid_words):
        val = diff_function(user_word, w, limit)
        diff_dict[val] = w

    min_diff = min(diff_dict)

    if min_diff <= limit:
        return diff_dict[min_diff]
    return user_word
    # END PROBLEM 5


def swap_diff(start, goal, limit):
    """
    A diff function for autocorrect that determines how many letters
    in START need to be substituted to create GOAL, then adds the difference in
    their lengths.
    """
    # BEGIN PROBLEM 6
    if start == goal:
        return 0
    if limit == 0:
        return 1
    if start == '' or goal == '':
        return max(len(start), len(goal))

    # Sets change to either True or False (1 or 0)
    change = start[0] != goal[0]

    return change + swap_diff(start[1:], goal[1:], limit - change)
    # END PROBLEM 6


def edit_diff(start, goal, limit):
    """A diff function that computes the edit distance from START to GOAL."""
    # BEGIN PROBLEM 7
    if start == goal:
        return 0
    if limit == 0:
        return 1
    if start == '' or goal == '':
        return max(len(start), len(goal))

    # Sets change to either True or False (1 or 0)
    change = start[0] != goal[0]

    add_diff = 1 + edit_diff(start, goal[1:], limit - 1)
    remove_diff = 1 + edit_diff(start[1:], goal, limit - 1)
    substitute_diff = change + edit_diff(start[1:], goal[1:], limit - change)

    return min(add_diff, remove_diff, substitute_diff)
    # END PROBLEM 7


def final_diff(start, goal, limit):
    """A diff function. If you implement this function, it will be used."""
    assert False, 'Remove this line to use your final_diff function'


def report_progress(typed, prompt, id, send):
    """Send a report of your id and progress so far to the multiplayer server."""
    # BEGIN PROBLEM 8
    count = 0
    for i in range(len(typed)):
        if typed[i] == prompt[i]:
            count += 1
        else:
            break

    progress = count / len(prompt)
    send({'id': id, 'progress': progress})
    return progress
    # END PROBLEM 8


def fastest_words_report(word_times):
    """Return a text description of the fastest words typed by each player."""
    fastest = fastest_words(word_times)
    report = ''
    for i in range(len(fastest)):
        words = ','.join(fastest[i])
        report += 'Player {} typed these fastest: {}\n'.format(i + 1, words)
    return report


def fastest_words(word_times, margin=1e-5):
    """A list of which words each player typed fastest."""
    n_players = len(word_times)
    n_words = len(word_times[0]) - 1
    assert all(len(times) == n_words + 1 for times in word_times)
    assert margin > 0
    # BEGIN PROBLEM 9

    # Construct empty return list
    results = [[] for _ in word_times]

    # Loops through words after 'START'
    for w in range(1, n_words + 1):

        # Computes player typing times for current word and gets fastest
        typing_times = []
        for p in range(n_players):
            player_time = elapsed_time(word_times[p][w]) - elapsed_time(word_times[p][w - 1])
            typing_times.append(player_time)
        fastest_time = min(typing_times)

        # Appends words to results
        for p in range(n_players):
            if typing_times[p] == fastest_time or abs(fastest_time - typing_times[p]) <= margin:
                item = word(word_times[0][w])
                results[p].append(item)

    return results
    # END PROBLEM 9


def word_time(word, elapsed_time):
    """A data abstraction for the elapsed time that a player finished a word."""
    return [word, elapsed_time]


def word(word_time):
    """An accessor function for the word of a word_time."""
    return word_time[0]


def elapsed_time(word_time):
    """An accessor function for the elapsed time of a word_time."""
    return word_time[1]


enable_multiplayer = False  # Change to True when you


##########################
# Command Line Interface #
##########################


def run_typing_test(topics):
    """Measure typing speed and accuracy on the command line."""
    paragraphs = lines_from_file('data/sample_paragraphs.txt')
    select = lambda p: True
    if topics:
        select = about(topics)
    i = 0
    while True:
        reference = choose(paragraphs, select, i)
        if not reference:
            print('No more paragraphs about', topics, 'are available.')
            return
        print('Type the following paragraph and then press enter/return.')
        print('If you only type part of it, you will be scored only on that part.\n')
        print(reference)
        print()

        start = datetime.now()
        typed = input()
        if not typed:
            print('Goodbye.')
            return
        print()

        elapsed = (datetime.now() - start).total_seconds()
        print("Nice work!")
        print('Words per minute:', wpm(typed, elapsed))
        print('Accuracy:        ', accuracy(typed, reference))

        print('\nPress enter/return for the next paragraph or type q to quit.')
        if input().strip() == 'q':
            return
        i += 1


@main
def run(*args):
    """Read in the command-line argument and calls corresponding functions."""
    import argparse
    parser = argparse.ArgumentParser(description="Typing Test")
    parser.add_argument('topic', help="Topic word", nargs='*')
    parser.add_argument('-t', help="Run typing test", action='store_true')

    args = parser.parse_args()
    if args.t:
        run_typing_test(args.topic)
