import sys

rows = int(sys.argv[1])
cols = int(sys.argv[2])
spacing = int(sys.argv[3])

alpha = " abcdefghijklmnopqrstuvwxyz"

def lbl(r, c):
    return alpha[r] + alpha[c]

print(rows * cols,
      rows * (cols - 1) + cols * (rows - 1) + (rows - 1) * (cols -1))

for r in range(1, rows + 1):
    for c in range(1, cols + 1):
        print(spacing*c, spacing*r)
        print(lbl(r, c))


for r in range(rows):
    for k in range(r * cols, (r + 1) * cols - 1):
        print(k, k + 1)
        print(1)

for c in range(cols):
    for k in range(c, c + cols * (rows-1), cols):
        print(k, k + cols)
        print(1)

for r in range(rows - 1):
    for c in range(cols - 1):
        print(r * cols + c, r * cols + c + cols + 1)
        print(1.42)
