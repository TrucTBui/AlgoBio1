import matplotlib.pyplot as plt
import numpy as np
import textwrap

def runtime_comparison():
    indices = np.array([1, 2])
    runtimes = np.array([5636, 56])

    bar_width = 0.9
    colors1 = ['tab:orange', 'tab:orange']
    fig, ax = plt.subplots(figsize=(7, 10))
    bars1 = ax.bar(indices, runtimes, bar_width, color=colors1, label='Gruppe 1')
    ax.set_ylabel('Runtime in ms', fontsize=17)
    ax.set_xticks(np.concatenate([indices + bar_width - 0.9]))
    labels = ['DFS', 'Union-Find']
    wrapped_labels = [textwrap.fill(label, 10) for label in labels]
    ax.set_xticklabels(wrapped_labels, fontsize=13)
    ax.set_ylim(0, 6000)

    for bar, value, label in zip(bars1, runtimes, labels):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=13)

    ax.tick_params(axis='y', labelsize=13)
    ax.set_title('Runtime Comparison', fontsize=17)
    print("done")
    plt.savefig('runtime_comparison_DFS_UnionFind.png')
    # plt.show()

if __name__ == '__main__':
    runtime_comparison()