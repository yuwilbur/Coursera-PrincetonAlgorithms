#pragma once
#include <vector>

class QuickUnion
{
public:
  QuickUnion(int N);

  void PrintArray();

  bool IsConnected(int p, int q);
  void Connect(int p, int q);

private:
  struct Node {
    int size;
    int root;
  };
  int root(int i);

  std::vector<Node> ids_;
};