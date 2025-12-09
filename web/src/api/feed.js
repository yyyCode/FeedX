import http from './http';

export const fetchFeed = (userId, size = 6) =>
  http.get(`/feed/${userId}`, { params: { size } });

