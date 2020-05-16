using FlowApp.Business.Abstract;
using FlowApp.Core.DTOs;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;
using System.Threading.Tasks;

namespace FlowApp.Business.Services
{
    public class RateService : IRateService
    {
        public readonly IRateRepository _rateRepository;

        public RateService(IRateRepository rateRepository)
        {
            _rateRepository = rateRepository;
        }

        public Task<bool> Any(int postId)
        {
            return _rateRepository.Any(x => x.PostId == postId);
        }

        public Task<bool> Any(int postId,int userId)
        {
            return _rateRepository.Any(x => x.PostId == postId && x.UserId == userId && x.Status == true);
        }

        public Rating GetRate(int postId)
        {
            return _rateRepository.Get(x => x.PostId == postId && x.Status == true);
        }

        public Rating GetRate(int postId,int userId)
        {
            return _rateRepository.Get(x => x.PostId == postId && x.UserId == userId && x.Status == true);
        }

        public Rating Save(Rating rating)
        {
            var _rating = _rateRepository.Add(rating);
            return _rating;
        }

        public Rating Update(Rating rating)
        {
            var _rating = _rateRepository.Update(rating);
            return _rating;
        }
    }
}