import matplotlib.pyplot as plt

def plot_cities(filename):
    # Read the city data from the file
    with open(filename, 'r') as f:
        cities = [line.strip().split() for line in f]

    # Extract the coordinates of each city
    x_coords = [float(city[1]) for city in cities[:-1]]
    y_coords = [float(city[2]) for city in cities[:-1]]

    # Title of graph
    title = cities[-1][0]

    # Turn off both the x and y axis
    # plt.axis('off')

    # Create a plot of the cities
    plt.plot(x_coords, y_coords, 'o', color='red')

    # Connect each city to its neighbor
    for i in range(len(cities)-1):
        start = i
        end = (i + 1) % len(cities[:-1])
        plt.plot([x_coords[start], x_coords[end]], [y_coords[start], y_coords[end]], '-', color='black')

    # Set the title of the plot
    plt.title(title)

    # Display the plot
    plt.show()

plot_cities('output.txt')
