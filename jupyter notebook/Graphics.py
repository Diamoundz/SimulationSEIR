import csv
import matplotlib.pyplot as plt

def gen_graph(n):
    # Read the CSV file
    with open(f'../data/data{n}.csv', newline='') as csvfile:
        reader = csv.reader(csvfile)
        
        # Get the header
        header = next(reader)
        
        # Get the data
        data = [row for row in reader]

    # Transpose the data to separate columns
    time_steps = [i for i in range(len(data))]
    columns_data = [[int(row[i]) for row in data] for i in range(0, len(header))]  # Fix indexing here

    # Plotting
    plt.figure(figsize=(10, 6))
    for i, column_data in enumerate(columns_data):
        plt.plot(time_steps, column_data, label=header[i], linestyle='-')

    title = 'SEIR Model ' + str(n)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.show()