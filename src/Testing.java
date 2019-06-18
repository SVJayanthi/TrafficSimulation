
public class Testing {
    public static void main(String[] args) {
	//Variable Instantiation
    int vMax = 5;
    int MSpaces = 1000;
    int NCars = 100;
    int Trials = 10;
    double pCrticial = (1/3);

    int positionArray[] = new int[NCars];
    int velocityCurrentArray[] = new int[NCars];
    int velocityPreviousArray[] = new int[NCars];
    
    int initPos = 1;
    boolean possibleRepeat;

    for(int i=0; i < positionArray.length; i++) {
    	possibleRepeat = true;
    	while (possibleRepeat) {
            initPos = (int) Math.max(((Math.random()) * MSpaces), 1);
            possibleRepeat = false;
            for (int j = 0; j < positionArray.length; j++) {
            	if (positionArray[j] == initPos) {
                    possibleRepeat = true;
                }
            }
        }
        positionArray[i] = initPos;
        //positionArray[i] = ((i+1)*10);
        velocityPreviousArray[i] = 0;
    }
    
    
	
	int difValue;
	int minDist;

for (int n=1; n<Trials; n++) {
for(int i=0; i<positionArray.length; i++) {
//series.add(positionArray[i], (Trials - n + 1));
velocityCurrentArray[i] = Math.min((velocityPreviousArray[i]+1), vMax);
minDist = Math.abs(positionArray[0] - positionArray[i]);
for (int j=1; j<positionArray.length; j++) {
	if (j==i) {
		j++;
		if (j==positionArray.length) {
			break;
		}
	}
	difValue = Math.abs(positionArray[j] - positionArray[i]);
	if (difValue < minDist) {
		if (positionArray[j] - positionArray[i] > 0) {
    		minDist = difValue;
		}

	}
}
/*
int nextPos = (i+1);
if ((i+1) == positionArray.length) {nextPos=0;}
if ((positionArray[nextPos] - positionArray[i]) < 0) {
    distance = positionArray[nextPos] - positionArray[i] + MSpaces;
} else {
    distance = positionArray[nextPos] - positionArray[i];
}
*/
velocityCurrentArray[i] = Math.min((velocityCurrentArray[i]), (minDist-1));
if (Math.random() >= pCrticial) {
    velocityCurrentArray[i] = Math.max(0, (velocityCurrentArray[i]-1));
}
positionArray[i] = positionArray[i] + velocityCurrentArray[i];

if (positionArray[i] > MSpaces) {
    positionArray[i] = positionArray[i] - MSpaces;
}
}
velocityPreviousArray = velocityCurrentArray.clone();
}
}
}
