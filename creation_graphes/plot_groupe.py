import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator

prog_name = {
    "Pi": "Pi.java",
    "MW" : "MasterSocket / WorkerSocket",
    "A102" : "Assignment 102"
}

colors = ['orange', 'green', 'blue', 'black']

tabDataFileNames = [["Pi_SFo720720",
                     "Pi_SFo7207200",
                     "Pi_SFo72072000",
                     "Pi_SFo720720000"],
                    ["MW_SFo720720",
                     "MW_SFo7207200",
                     "MW_SFo72072000",
                     "MW_SFo720720000"],
                    ["A102_SFo720720",
                     "A102_SFo7207200",
                     "A102_SFo72072000"]]

for dataFileNames in tabDataFileNames:
    allSpeedUps: list[list[float]] = list()
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

        allSpeedUps.append(speedups)

    plt.figure(figsize=(13, 10))
    i: int = 0
    for speedups in allSpeedUps:
        plt.plot(range(1, len(speedups)+1), speedups, marker='o', linestyle='-', color=colors[i], label=f'Charge = {dataFileNames[i].split("_")[1][3:]}')
        i += 1

    plt.plot(range(1, len(allSpeedUps[0])+1), range(1, len(allSpeedUps[0])+1), linestyle='--', color='r', label='Target Speedup')

    plt.xlabel("Nombre de processus")
    plt.ylabel("Speedup ( T1/Tp )")
    plt.title(
        f"Comparaison de l'évolution du Speedup médian du programme {prog_name[dataFileName.split("_")[0]]} \n"
        f"en fonction du nombre de processus pour différentes charges de travail sur un test de scalabilité forte")
    plt.legend()
    plt.grid(True)
    plt.xticks(range(1, len(allSpeedUps[0]) + 1))
    plt.yticks(range(1, len(allSpeedUps[0]) + 1))

    plt.savefig("graph/groupe/graph" + dataFileNames[1].split("_")[0] + "_SFo.png", dpi=300)
    plt.close()


tabDataFileNames = [["Pi_SFa720720",
                     "Pi_SFa7207200",
                     "Pi_SFa72072000",
                     "Pi_SFa720720000"],
                    ["MW_SFa720720",
                     "MW_SFa7207200",
                     "MW_SFa72072000",
                     "MW_SFa720720000"],
                    ["A102_SFa720720",
                     "A102_SFa7207200",
                     "A102_SFa72072000"]]

for dataFileNames in tabDataFileNames:
    allSpeedUps: list[list[float]] = list()
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

        allSpeedUps.append(speedups)

    plt.figure(figsize=(13, 10))
    i: int = 0
    for speedups in allSpeedUps:
        plt.plot(range(1, len(speedups)+1), speedups, marker='o', linestyle='-', color=colors[i], label=f'Charge = {dataFileNames[i].split("_")[1][3:]}')
        i += 1

    plt.plot(range(1, len(allSpeedUps[0])+1), [1]*len(allSpeedUps[0]), linestyle='--', color='r', label='Target Speedup')

    plt.xlabel("Nombre de processus")
    plt.ylabel("Speedup ( T1/Tp )")
    plt.title(
        f"Comparaison de l'évolution du Speedup médian du programme {prog_name[dataFileName.split("_")[0]]} \n"
        f"en fonction du nombre de processus pour différentes charges de travail sur un test de scalabilité faible")
    plt.legend()
    plt.grid(True)
    plt.xticks(range(1, len(allSpeedUps[0]) + 1))
    plt.yticks([i*0.05 for i in range(1, 29)])

    plt.savefig("graph/groupe/graph" + dataFileNames[1].split("_")[0] + "_SFa.png", dpi=300)
    plt.close()
