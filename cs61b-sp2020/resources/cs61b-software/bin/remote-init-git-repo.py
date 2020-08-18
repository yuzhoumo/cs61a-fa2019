import re, sys
from subprocess import DEVNULL, check_output, check_call, CalledProcessError
from os import environ, chdir
from os.path import isdir

HOST = 'cs61b-taa@derby.cs.berkeley.edu'

def info(msg, *args):
    print(msg.format(*args), file=sys.stderr)

def error(msg, *args):
    info("Error: " + msg, *args)
    sys.exit(1)

def getRegField(prompt):
    print(prompt + ": ", end="")
    sys.stdout.flush()
    return sys.stdin.readline().strip()

def call(command, *args):
    command = command.format(*args)
    print(command, file=sys.stderr)
    check_call(command, shell=True)

resp = ""
while resp not in ['y', 'Y', 'yes', 'YES']:
    try:
        firstName = getRegField(r'First name(s)')
        lastName = getRegField('Last name')
        email = getRegField('Email')
        me = ""
        while not me.startswith("cs61b-") or not len(me) == 9:
            me = getRegField('CS61B login (cs61b-***)')
            if not me.startswith("cs61b-") or not len(me) == 9:
                print("Please try again. Enter your full login of the form 'cs61b-***'")
        print("""\
First name: {}
Last name: {}
Email: {}
CS61B login (cs61b-***): {}
Correct? [y/n] """.format(firstName, lastName, email, me), end="")
        sys.stdout.flush()
        resp = sys.stdin.readline().strip()
    except KeyboardInterrupt:
        sys.exit(1)

repo = 'repo' if len(sys.argv) < 2 else sys.argv[1]

chdir(environ['HOME'])
if isdir(repo):
    error("{} already exists", repo)

try:
    call('git clone {}:students/{} {}', HOST, me, repo)
except CalledProcessError:
    error("Could not create and clone repository")

chdir(repo)
try:
    call('git config --global user.name "{} {}"', firstName, lastName)
    call('git config --global user.email "{}"', email)
    call('git config --global push.default simple')
except CalledProcessError:
    error("Global Git configuration failed")

try:
    call('git remote add shared {}:shared', HOST)
    call('git fetch shared')
except CalledProcessError:
    error("Could not set up access to shared class repository")

try:
    call('git checkout')
    sys.exit(0)
except CalledProcessError:
    info("Could not checkout master branch.  Trying to create it.")

try:
    call('git checkout -b master shared/Initial')
except CalledProcessError:
    error("Could not set up initial empty branch")

try:
    call('git push -u origin master')
except CalledProcessError:
    error("Could not push initial empty branch")

