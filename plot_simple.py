import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator

prog_name = {
    "Pi": "Pi.java",
    "MW" : "MasterSocket / WorkerSocket",
    "A102" : "Assignment 102"
}

dataFileNames = ["Pi_SFo720720",
                 "Pi_SFo7207200",
                 "Pi_SFo72072000",
                 "Pi_SFo720720000",
                 "MW_SFo720720",
                 "MW_SFo7207200",
                 "MW_SFo72072000",
                 "MW_SFo720720000",
                 "A102_SFo720720",
                 "A102_SFo7207200",
                 "A102_SFo72072000"]

for dataFileName in dataFileNames:
    durations: dict[int, list[int]] = dict()
    with open("data/data" + dataFileName + ".csv") as datafile:
        datafile.readline()
        line: str = datafile.readline()
        while line != "\n" and line != "":
            data: list[str] = line.split(" ")
            if int(data[1]) in durations.keys():
                durations[int(data[1])].append(int(data[2]))
            else:
                durations[int(data[1])] = [int(data[2])]
            line = datafile.readline()

    speedups: list[float] = list()
    for key in durations.keys():
        durations[key].sort()

    speedups.append(1.)

    for key in sorted(list(durations.keys())):
        if key != 1:
            speedups.append(durations[1][5]/durations[key][5])

    plt.figure(figsize=(13, 10))
    plt.plot(range(1, len(speedups)+1), speedups, marker='o', linestyle='-', color='b', label='Speedup médian')
    plt.plot(range(1, len(speedups)+1), range(1, len(speedups)+1), linestyle='--', color='r', label='Target Speedup')

    plt.xlabel("Nombre de processus")
    plt.ylabel("Speedup ( T1/Tp )")
    plt.title(f"Évolution du Speedup médian du programme {prog_name[dataFileName.split("_")[0]]} en fonction du nombre de processus \n"
              f"pour {dataFileName.split("_")[1][3:]} points calculés sur un test de scalabilité forte")
    plt.legend()
    plt.grid(True)
    plt.xticks(range(1, len(speedups) + 1))
    plt.yticks(range(1, len(speedups) + 1))

    plt.savefig("graph/simple/graph" + dataFileName + ".png", dpi=300)
    plt.close()
    print(dataFileName)


dataFileNames = ["Pi_SFa720720",
                 "Pi_SFa7207200",
                 "Pi_SFa72072000",
                 "Pi_SFa720720000",
                 "MW_SFa720720",
                 "MW_SFa7207200",
                 "MW_SFa72072000",
                 "MW_SFa720720000",
                 "A102_SFa720720",
                 "A102_SFa7207200",
                 "A102_SFa72072000"]

for dataFileName in dataFileNames:
    durations: dict[int, list[int]] = dict()
    with open("data/data" + dataFileName + ".csv") as datafile:
        datafile.readline()
        line: str = datafile.readline()
        while line != "\n" and line != "":
            data: list[str] = line.split(" ")
            if int(data[1]) in durations.keys():
                durations[int(data[1])].append(int(data[2]))
            else:
                durations[int(data[1])] = [int(data[2])]
            line = datafile.readline()

    speedups: list[float] = list()
    for key in durations.keys():
        durations[key].sort()

    speedups.append(1.)

    for key in sorted(list(durations.keys())):
        if key != 1:
            speedups.append(durations[1][5]/durations[key][5])

    plt.figure(figsize=(13, 10))
    plt.plot(range(1, len(speedups)+1), speedups, marker='o', linestyle='-', color='b', label='Speedup médian')
    plt.plot(range(1, len(speedups)+1), [1]*len(speedups), linestyle='--', color='r', label='Target Speedup')

    plt.xlabel("Nombre de processus")
    plt.ylabel("Speedup ( T1/Tp )")
    plt.title(f"Évolution du Speedup médian du programme {prog_name[dataFileName.split("_")[0]]} en fonction du nombre de processus \n"
              f"pour {dataFileName.split("_")[1][3:]} points calculés sur un test de scalabilité faible")
    plt.legend()
    plt.grid(True)
    plt.xticks(range(1, len(speedups) + 1))
    plt.yticks([i*0.05 for i in range(1, 29)])

    plt.savefig("graph/simple/graph" + dataFileName + ".png", dpi=300)
    plt.close()
    print(dataFileName)
