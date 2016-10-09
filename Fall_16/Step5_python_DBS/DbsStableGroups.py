import sys, re
import numpy as np

from sklearn.cluster import DBSCAN
from sklearn import metrics

##########################################
# arg 1 - lon lat array ll file
# arg 2 - input file, e.g. dayGroup file
# arg 3 - output file
#########################################

lonLat = __import__(sys.argv[1])
inputFile = open(sys.argv[2],'r')
outputFile = open(sys.argv[3], 'w')

print(inputFile)
print(outputFile)

np_ll = np.array(lonLat.ll)

ll_len = np_ll.shape[0]


#eps = 500 meters
#db = DBSCAN(eps=0.5,min_samples=((ll_len/8)),metric='haversine',algorithm='ball_tree').fit(np_ll)
#db = DBSCAN(eps=0.5,min_samples=65,metric='haversine',algorithm='ball_tree').fit(np_ll)
db = DBSCAN(eps=0.5,min_samples=100,metric='haversine',algorithm='ball_tree').fit(np_ll)
labels = np.array(db.labels_)
n_clusters_ = len(set(labels))  - (1 if -1 in labels else 0)
print("Number of Day Stable points with no clusters: "+str(len(labels[labels == -1])))
#outputFile.write(

for z in set(labels[labels != -1]):
	#print(np.mean(np_ll[labels == z],axis=0))
	#print(len(labels[labels == z]))
	#outputFile.write("Cluster "+str(z+1)+"- Mean :"+str(np.mean(np_ll[labels == z],axis=0))+"- Number of Points in Cluster :"+str(len(labels[labels == z]))+"\n")
	print("Cluster "+str(z+1)+"- Mean :"+str(np.mean(np_ll[labels == z],axis=0))+"- Number of Points in Cluster :"+str(len(labels[labels == z]))+"\n")

for i in range(5427):
	line = inputFile.readline()
	line=line.replace("\n","")
	line2=line.split(",")
	lon = float(line2[1])
	lat = float(line2[2])
	if(lon <> np_ll[i][0] or lat <> np_ll[i][1]):
		print("lon and lat in file don't match the lon lat in ll")
		print(str(lon)+","+str(lat)+"||"+str(np_ll[i][0])+","+str(np_ll[i][1]))
	else:
		outputFile.write(str(np_ll[i]) + " || " + line + " || " + str(labels[i]) + "\n")
	#print(str(np_ll[i]) + "||" + line + "||" + str(labels[i]))
