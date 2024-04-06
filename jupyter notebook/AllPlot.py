import csv
import matplotlib.pyplot as plt

def pie_chart(n):
    # Read the CSV file
    with open(f'../data/data{n}.csv', newline='') as csvfile:
        reader = csv.reader(csvfile)
        
        # Get the header
        header = next(reader)
        
        # Get the data
        data = [row for row in reader]

    # Summing up values for each category
    totals = [sum(int(row[i]) for row in data) for i in range(0, len(header))]  # Fix indexing here

    # Plotting
    plt.figure(figsize=(8, 8))
    plt.pie(totals, labels=header, autopct='%1.1f%%', startangle=140)
    title = 'Pie Chart ' + str(n)
    plt.title(title)
    plt.axis('equal')  # Equal aspect ratio ensures that pie is drawn as a circle.
    plt.show()

def histogram(n):
    # Read the CSV file
    with open(f'../data/data{n}.csv', newline='') as csvfile:
        reader = csv.reader(csvfile)
        
        # Get the header
        header = next(reader)
        
        # Get the data
        data = [row for row in reader]

    # Transpose the data to separate columns
    columns_data = [[int(row[i]) for row in data] for i in range(0, len(header))]  # Fix indexing here

    # Plotting
    plt.figure(figsize=(10, 6))
    for i, column_data in enumerate(columns_data):
        plt.hist(column_data, bins=10, alpha=0.5, label=header[i])

    title = 'Histogram ' + str(n)
    plt.title(title)
    plt.xlabel('Value')
    plt.ylabel('Frequency')
    plt.legend()
    plt.grid(True)
    plt.show()

def scatter_plot(n):
    # Read the CSV file
    with open(f'../data/data{n}.csv', newline='') as csvfile:
        reader = csv.reader(csvfile)
        
        # Get the header
        header = next(reader)
        
        # Get the data
        data = [row for row in reader]

    # Transpose the data to separate columns
    columns_data = [[int(row[i]) for row in data] for i in range(0, len(header))]  # Fix indexing here

    # Plotting
    plt.figure(figsize=(10, 6))
    plt.scatter(columns_data[0], columns_data[1], label=f'{header[0]} vs {header[1]}')
    title = 'Scatter Plot ' + str(n)
    plt.title(title)
    plt.xlabel(header[0])
    plt.ylabel(header[1])
    plt.legend()
    plt.grid(True)
    plt.show()