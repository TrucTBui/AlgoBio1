import matplotlib.pyplot as plt
import numpy as np
import textwrap

def runtime_comparison():
    indices = np.array([1, 2, 3, 4, 5])
    runtimes = np.array([27, 35, 13, 9, 2])

    bar_width = 0.9
    colors1 = ['tab:orange', 'tab:orange', 'tab:orange', 'tab:orange']
    fig, ax = plt.subplots(figsize=(8, 6))
    bars1 = ax.bar(indices, runtimes, bar_width, color=colors1, label='Gruppe 1')
    ax.set_ylabel('Runtime in μs', fontsize=17)
    ax.set_xticks(np.concatenate([indices + bar_width - 0.9]))
    labels = ['Naive', 'Naive Recursive', 'Dynamic Programming', 'Divide-and-Conquer', 'Optimal']
    wrapped_labels = [textwrap.fill(label, 10) for label in labels]
    ax.set_xticklabels(wrapped_labels, fontsize=13)
    ax.set_ylim(0, 40)

    for bar, value, label in zip(bars1, runtimes, labels):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=13)

    ax.tick_params(axis='y', labelsize=13)
    ax.set_title('Mean Runtime Comparison', fontsize=17)

    plt.savefig('runtime_comparison_graph.png')
    # plt.show()

if __name__ == '__main__':
    runtime_comparison()