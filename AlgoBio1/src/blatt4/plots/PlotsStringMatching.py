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


def generate_random_substring(input_string, n):
    if n > len(input_string)//2:
        raise ValueError("Substring length cannot be greater than half of the input string length")

    start_index = random.randint(len(input_string)//2, len(input_string) - n) #a substring at the second half of the text
    return input_string[start_index:start_index + n]

# Lengths of text and pattern to test
text_lengths = [200,1000, 2000, 4000, 8000, 10000, 20000, 40000]
pattern_lengths = [10, 20]  # List of pattern lengths to test


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
                pattern = generate_random_substring(text,pattern_length)
                start_time = time.time()
                subprocess.run(
                    ['java', '-jar',
                     "/home/trucbui/GitHub/AlgoBio1Git/AlgoBio1/out/artifacts/Naive_jar/Naive.jar",
                     "-t", text, "-p", pattern], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                end_time = time.time()
                avg_runtime += (end_time - start_time)
            avg_runtime /= 10
            runtimes.append(avg_runtime)

        # Plot the results for current pattern length
        plt.plot(text_lengths, runtimes, marker='o', label=f'Pattern Length = {pattern_length}')

    plt.title('Naive Algorithm Runtime Using ' + used_alpabet,size=20)
    plt.xlabel('Text Length',size=15)
    plt.ylabel('Runtime (seconds)',size=15)
    plt.legend()
    plt.grid(True)
    plt.xticks(text_lengths, rotation=60)
    plt.tight_layout()
    plt.savefig('/home/trucbui/PycharmProjects/Plots/naive_string_matching_runtime_' + str(plotname)+ ".png")
    #plt.show()


def plot_runtime_comparison(alphabet):
    used_alpabet = ""
    plotname = 0
    if alphabet == ALPHABET1:
        used_alpabet = "{0,1} Alphabet"
        plotname = 1
    elif alphabet == ALPHABET2:
        used_alpabet = "{A,T,G,C} Alphabet"
        plotname = 2
    elif alphabet == ALPHABET3:
        used_alpabet = "Alphanumeric Alphabet"
        plotname = 3

    plt.figure(figsize=(15, 8))  # Adjust figure size if needed

    for pattern_length in pattern_lengths:
        naive_runtimes = []
        kmp_runtimes = []

        for length in text_lengths:
            # Generate random text and pattern
            text = generate_random_string(length, alphabet)
            pattern = generate_random_substring(text, pattern_length)

            # Measure runtime for Naive String Matching
            start_time = time.time()
            subprocess.run(
                ['java', '-jar',
                 "/home/trucbui/GitHub/AlgoBio1Git/AlgoBio1/out/artifacts/Naive_jar/Naive.jar",
                 "-t", text, "-p", pattern], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            end_time = time.time()
            naive_runtime = end_time - start_time
            naive_runtimes.append(naive_runtime)

            # Measure runtime for KMP Algorithm
            start_time = time.time()
            subprocess.run(
                ['java', '-jar',
                 "/home/trucbui/GitHub/AlgoBio1Git/AlgoBio1/out/artifacts/KMP_jar/KMP.jar",
                 "-t", text, "-p", pattern], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            end_time = time.time()
            kmp_runtime = end_time - start_time
            kmp_runtimes.append(kmp_runtime)

        # Plot the results for current pattern length
        plt.plot(text_lengths, naive_runtimes, marker='o', linestyle='-',
                 label=f'Naive (Pattern Length = {pattern_length})')
        plt.plot(text_lengths, kmp_runtimes, marker='s', linestyle='--',
                 label=f'KMP (Pattern Length = {pattern_length})')

    plt.title('Naive vs KMP Algorithm Runtime Comparison With ' + used_alpabet,size=20)
    plt.xlabel('Text Length')
    plt.ylabel('Runtime (seconds)')
    plt.legend()
    plt.grid(True)
    plt.xticks(text_lengths, rotation=60)
    plt.tight_layout()

    # Save the plot to a file
    plt.savefig('/home/trucbui/PycharmProjects/Plots/runtime_comparison_' + str(plotname)+".png")

    plt.show()


# Call the function to plot
#plot_runtime(ALPHABET1)
#plot_runtime(ALPHABET2)
#plot_runtime(ALPHABET3)

plot_runtime_comparison(ALPHABET1)
plot_runtime_comparison(ALPHABET2)
plot_runtime_comparison(ALPHABET3)