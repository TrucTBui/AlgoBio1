import argparse
import matplotlib.pyplot as plt

parser = argparse.ArgumentParser()
parser.add_argument('-methods', type=str, nargs='+', help='Different methods for the bar plot')
parser.add_argument('-runtime', type=int, nargs='+', help='Corresponding runtime for the methods')
parser.add_argument('-title', type=str, help='Title of the plot')

args = parser.parse_args()

def plot(categories, values, title):
    plt.bar(categories, values, color='pink')
    plt.xticks(rotation=60)

    plt.xlabel('Method')
    plt.ylabel('Runtime in μs')
    
    plt.title(title)
    plt.grid(True)
    plt.show()

if __name__ == '__main__':
    plot(args.methods, args.runtime, args.title)
