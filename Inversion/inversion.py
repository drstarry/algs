#!/usr/bin/env python2
# encoding: utf8

import IPython

def prepare_data():
    data = list()
    with open('IntegerArray.txt') as f:
        for line in f.readlines():
            data.append(int(line.strip()))
    return data


def sort_count(left_division, right_division, count):
    """the inversion count of (i,y) is the current number of left_division when curser j=y
    """

    i, j, k = 0, 0, 0
    left_length = len(left_division)
    right_length = len(right_division)
    total_length = left_length + right_length
    sorted_array = [0 for x in range(total_length)]

    while i<left_length and j<right_length and k<total_length:
        if left_division[i] < right_division[j]:
            sorted_array[k] = left_division[i]
            i += 1
        else:
            sorted_array[k] = right_division[j]
            count += left_length - i
            j += 1
        k += 1

    while i<left_length and k<total_length:
        sorted_array[k] = left_division[i]
        i += 1
        k += 1

    while j<right_length and k<total_length:
        sorted_array[k] = right_division[j]
        count += left_length - i
        j += 1
        k += 1

    return count, sorted_array;


def count_the_inversions(array,count):
    length = len(array)
    if length == 1:
        return 0, array
    mid = length//2
    left_division = array[:mid]
    right_division = array[mid:]

    left_count, sorted_left_division = count_the_inversions(left_division, count)
    right_count, sorted_right_division = count_the_inversions(right_division, count)
    both_count, sorted_array = sort_count(sorted_left_division, sorted_right_division, count)

    return left_count+right_count+both_count, sorted_array


def count_the_inversion_brute_force(array):
    pass


if __name__ == '__main__':
    array = prepare_data()
    initial_count = 0
    count, X = count_the_inversions(array, initial_count)
    print count
