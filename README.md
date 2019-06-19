# TrafficSimulation

![graph.png](https://github.com/SVJayanthi/TrafficSimulation/blob/master/output/NagelSchreckenbergTraffic.png)

## Author
Sravan Jayanthi

## Nagel-Schreckenberg
The famous Nagel-Schreckenberg model, known for its use of Original Monte Carlo to model random driver behavior in traffic, was recreated using Java to gain a greater understanding of the application of Monte Carlo theory. The simulation was setup with a random variable that would influence the motion of driver behavior. Eventually, the distribution of cars deviates from the starting conditions due to the influence of the random variable. The motion of the cars are governed by four rules: each car seeks to increase speed, their speed cannot exceed the speed limit, they must slow down if there is a car ahead, and the probability p of the car slowing down. In each time step, the cars operate on these rules and are repeated until the simulation is complete. The final rule, the reduction of speed randomly, is where driver behavior will be simulated in causing potential traffic jams.

### Rules
1. Cars experience constant acceleration until velocity limit is reached
2. If the distance between a car and the vehicle ahead of it is smaller than the velocity, the velocity is reduced to avoid crashing
3. The speed of the cars is randomly reduced, representing the random variable that will simulate potential traffic jams
4. The cars are moved forward the number of cells equivalent to their velocity.

## Implementation

### Run the application:
Run `TrafficSimulation\src\TrafficExample.java` on a Java IDE.

### Code
Sample code of updating speed of a car facing traffic.

            velocityCurrentArray[i] = Math.min((velocityCurrentArray[i]), (minDist-1));
            double prop = Math.random();
            if (prop >= pCrit) {
                velocityCurrentArray[i] = Math.max(0, (velocityCurrentArray[i]-1));
            }
            positionArray[i] = positionArray[i] + velocityCurrentArray[i];
            if (positionArray[i] > MSpaces) {
                positionArray[i] = positionArray[i] - MSpaces;
            }

## Conclusions
The results of the simulations in give insight into why traffic develops and how it propagates through a highway. The random behavior of the driver to slow down has a direct chain effect on the motion of all the other drivers on the highway. Thus, the darker areas demonstrate areas of high traffic that sees many cars forced to slow down to prevent collision. The traffic eventually dissipates at the area of formation but the jam continues on going towards the back of the highway. The traffic propagates in the reverse direction of motion leading to many more cars getting stuck. However, it is the probability that the car will not slow down that allows the other cars to escape the traffic. 

## License
[GNU](LICENSE)
