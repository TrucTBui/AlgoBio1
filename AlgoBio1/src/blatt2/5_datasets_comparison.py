import matplotlib.pyplot as plt
import numpy as np
import textwrap
def graph_computer_compare():  # row: Truc,Antoaneta
    all_MSS_Truc = np.array([1,2])
    all_MSS_Antoaneta = np.array([2, 1])

    SMSS_Truc = np.array([3, 4])
    SMSS_Antoaneta = np.array([3, 4])

    minimal_SMSS_Truc = np.array([5, 6])
    minimal_SMSS_Antoaneta = np.array([3, 12])

    MSS_naive_Truc = np.array([7, 8])
    MSS_naive_Antoaneta = np.array([4, 18])

    MSS_naive_recursive_Truc = np.array([9, 10])
    MSS_naive_recursive_Antoaneta = np.array([4, 24])

    bar_width = 0.95  # with of each group
    colors2 = ['tab:blue', 'tab:orange']  # color the columns
    fig, ax = plt.subplots(figsize=(16, 6))  # size of graph

    bars1 = ax.bar(all_MSS_Truc, all_MSS_Antoaneta, bar_width, color=colors2, label='Gruppe 1')
    bars2 = ax.bar(SMSS_Truc + bar_width, SMSS_Antoaneta, bar_width, color=colors2, label='Gruppe 2')
    bars3 = ax.bar(minimal_SMSS_Truc + 2 * bar_width, minimal_SMSS_Antoaneta, bar_width, color=colors2)
    bars4 = ax.bar(MSS_naive_Truc + 3 * bar_width, MSS_naive_Antoaneta, bar_width, color=colors2)
    bars5 = ax.bar(MSS_naive_recursive_Truc + 4 * bar_width, MSS_naive_recursive_Antoaneta, bar_width, color=colors2)

    # ax.set_xlabel('Algorithms', fontsize=16)
    ax.set_ylabel('Time in ms', fontsize=18)

    group_positions = [all_MSS_Truc[0] + (all_MSS_Truc[-1] - all_MSS_Truc[0]) / 2,
                       SMSS_Truc[0] + (SMSS_Truc[-1] - SMSS_Truc[0]) / 2 + bar_width,
                       minimal_SMSS_Truc[0] + (minimal_SMSS_Truc[-1] - minimal_SMSS_Truc[0]) / 2 + 2 * bar_width,
                       MSS_naive_Truc[0] + (MSS_naive_Truc[-1] - MSS_naive_Truc[0]) / 2 + 3 * bar_width,
                       MSS_naive_recursive_Truc[0] + (MSS_naive_recursive_Truc[-1] - MSS_naive_recursive_Truc[0]) / 2 + 4 * bar_width]
    ax.set_xticks(group_positions)

    labels = ['50 cities', '100 cities', '150 cities', '200 cities', '250 cities']
    wrapped_labels = [textwrap.fill(label, 16) for label in labels]
    ax.set_xticklabels(wrapped_labels, fontsize=17)

    ax.set_ylim(0, 40)

    for bar, value in zip(bars1, all_MSS_Antoaneta):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=16)

    for bar, value in zip(bars2, SMSS_Antoaneta):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=16)

    for bar, value in zip(bars3, minimal_SMSS_Antoaneta):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=16)

    for bar, value in zip(bars4, MSS_naive_Antoaneta):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=16)

    for bar, value in zip(bars5, MSS_naive_recursive_Antoaneta):
        ax.text(bar.get_x() + bar.get_width() / 2, value, f'{value}', ha='center', va='bottom', fontsize=16)

    ax.tick_params(axis='y', labelsize=16)
    # ax.yaxis.set_major_locator(MultipleLocator(10))

    legend_handles = [plt.Line2D([0], [0], color=colors2[0], lw=6),
                      plt.Line2D([0], [0], color=colors2[1], lw=6)]

    legend_labels = ['Aufgabe 2', 'Aufgabe 3']
    ax.legend(legend_handles, legend_labels, fontsize=17)

    ax.set_title('Runtime comparison between 5 different data sets', fontsize=20)

    plt.savefig('number_of_cities_comparison.png')
    # plt.show() - for visualisation

if __name__ == '__main__':
    graph_computer_compare()


