#include "stdafx.h"
#include "QuickFind.h"
#include "QuickUnion.h"

void main() {
  QuickFind q1(10);
  q1.Connect(3, 7);
  q1.Connect(6, 7);
  q1.Connect(0, 9);
  q1.Connect(2, 4);
  q1.Connect(8, 7);
  q1.Connect(6, 5);
  q1.PrintArray();

  QuickUnion q2(10);
  q2.Connect(1, 7);
  q2.Connect(6, 0);
  q2.Connect(2, 8);
  q2.Connect(2, 3);
  q2.Connect(7, 6);
  q2.Connect(3, 5);
  q2.Connect(9, 4);
  q2.Connect(9, 8);
  q2.Connect(8, 6);
  q2.PrintArray();

  QuickUnion q3(10);
  q3.Connect(9, 8);
  q3.Connect(9, 7);
  q3.Connect(9, 2);
  q3.Connect(4, 0);
  q3.Connect(4, 9);
  q3.Connect(1, 5);
  q3.Connect(3, 6);
  q3.Connect(3, 1);
  q3.Connect(9, 3);
  q3.PrintArray();
}