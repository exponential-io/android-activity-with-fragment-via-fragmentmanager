#!/bin/bash

BASE_DIR=app/src/main/res

ORIENTATIONS=(
    layout-land
    layout-small 
    layout-small-land 
    layout-normal 
    layout-normal-land 
    layout-large 
    layout-large-land 
    layout-xlarge 
    layout-xlarge-land
    layout-sw600dp
)

DRAWABLES=(
    drawable-xxhdpi 
    drawable-xhdpi 
    drawable-hdpi 
    drawable-mdpi 
    drawable-ldpi
)

# Create all directories for size and orientation variations

for ORIENTATION in ${ORIENTATIONS[*]}
do
    mkdir ${BASE_DIR}/${ORIENTATION}
done

# Create all directories for density variations

for DRAWABLE in ${DRAWABLES[*]}
do
    mkdir ${BASE_DIR}/${DRAWABLE}
done

# Move existing resources to the correct directory

#if [ -d ${BASE_DIR}/drawable ] ; then
#    mv ${BASE_DIR}/drawable/* ${BASE_DIR}/drawable-mdpi/;
#    rmdir ${BASE_DIR}/drawable/;
#fi
#
#if [ -d ${BASE_DIR}/layout ] ; then
#    mv ${BASE_DIR}/layout/* ${BASE_DIR}/layout-normal/;
#    rmdir ${BASE_DIR}/layout/;
#fi
