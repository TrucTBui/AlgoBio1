import subprocess
import time
import random
import string
import matplotlib.pyplot as plt

ALPHABET1 = ['0', '1']
ALPHABET2 = ['A', 'T', 'G', 'C']
ALPHABET3 = list(string.ascii_letters + string.digits)

def generate_random_string(length, alphabet):
    return ''.join(random.choice(alphabet) for _ in range(length))

# Lengths of text and pattern to test
text_lengths = [100, 200, 500, 1000, 2000, 5000, 10000, 20000]
pattern_lengths = [5, 10, 15, 20]  # List of pattern lengths to test


def plot_runtime(alphabet):
    used_alpabet = ""
    plotname = 0
    if alphabet==ALPHABET1:
        used_alpabet="{0,1} Alphabet"
        plotname = 1
    elif alphabet==ALPHABET2:
        used_alpabet="{A,T,G,C} Alphabet"
        plotname = 2
    elif alphabet==ALPHABET3:
        used_alpabet="Alphanumeric Alphabet"
        plotname = 3

    plt.figure(figsize=(10, 6))  # Adjust figure size if needed

    for pattern_length in pattern_lengths:
        runtimes = []

        for length in text_lengths:
            avg_runtime = 0
            for _ in range(10):
                text = generate_random_string(length, alphabet)
                pattern = generate_random_string(pattern_length, alphabet)
                start_time = time.time()
                subprocess.run(
                    ['java', '-jar',
                     "/home/trucbui/GitHub/AlgoBio1Git/AlgoBio1/out/artifacts/NaiveStringMatching_jar_jar/NaiveStringMatching.jar",
                     "-t", text, "-p", pattern], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                end_time = time.time()
                avg_runtime += (end_time - start_time)
            avg_runtime /= 10
            runtimes.append(avg_runtime)

        # Plot the results for current pattern length
        plt.plot(text_lengths, runtimes, marker='o', label=f'Pattern Length = {pattern_length}')

    plt.title('Naive String Matching Runtime Using ' + used_alpabet,size=20)
    plt.xlabel('Text Length',size=15)
    plt.ylabel('Runtime (seconds)',size=15)
    plt.legend()
    plt.grid(True)
    #plt.xticks(text_lengths)
    plt.tight_layout()
    plt.savefig('/home/trucbui/PycharmProjects/Plots/naive_string_matching_runtime_' + str(plotname)+ ".png")
    #plt.show()


# Call the function to plot
plot_runtime(ALPHABET1)
plot_runtime(ALPHABET2)
plot_runtime(ALPHABET3)