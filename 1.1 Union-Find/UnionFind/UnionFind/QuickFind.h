#pragma once
#include <vector>

class QuickFind 
{
public:
  QuickFind(int N);

  void PrintArray();

  bool IsConnected(int p, int q);
  void Connect(int p, int q);

private:
   std::vector<int> ids_;
};