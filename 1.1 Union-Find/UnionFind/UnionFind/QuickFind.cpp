#include "stdafx.h"
#include "QuickFind.h"

QuickFind::QuickFind(int N) {
  ids_.resize(N);
  for (size_t i = 0; i < N; ++i) {
    ids_[i] = (int)i;
  }
}

bool QuickFind::IsConnected(int p, int q) {
  return ids_[p] == ids_[q];
}

void QuickFind::Connect(int p, int q) {
  if (ids_[p] == ids_[q])
    return;

  int pid = ids_[p];
  int qid = ids_[q];
  for (size_t i = 0; i < ids_.size(); ++i) {
    if (ids_[i] == pid) { ids_[i] = qid; }
  }
}

void QuickFind::PrintArray() {
  for (size_t i = 0; i < ids_.size(); ++i) {
    std::cout << ids_[i] << " ";
  }
  std::cout << std::endl;
}