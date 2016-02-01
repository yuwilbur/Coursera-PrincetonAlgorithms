#include "stdafx.h"
#include "QuickUnion.h"

QuickUnion::QuickUnion(int N) {
  ids_.resize(N);
  for (size_t i = 0; i < N; ++i) {
    ids_[i].root = (int)i;
    ids_[i].size = 1;
  }
}

bool QuickUnion::IsConnected(int p, int q) {
  return root(p) == root(q);
}

void QuickUnion::Connect(int p, int q) {
  int i = root(p);
  int j = root(q);
  if (i == j)
    return;

  if (ids_[i].size < ids_[j].size) {
    ids_[i].root = j;
    ids_[j].size += ids_[i].size;
  }
  else {
    ids_[j].root = i;
    ids_[i].size += ids_[j].size;
  }
}

void QuickUnion::PrintArray() {
  for (size_t i = 0; i < ids_.size(); ++i) {
    std::cout << ids_[i].root << " ";
  }
  std::cout << std::endl;
}

int QuickUnion::root(int i) {
  while (i != ids_[i].root) {
    i = ids_[i].root;
  }
  return i;
}