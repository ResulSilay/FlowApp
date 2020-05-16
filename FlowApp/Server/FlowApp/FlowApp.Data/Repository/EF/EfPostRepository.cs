using FlowApp.Core.Data.EF;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FlowApp.Data.Repository.EF
{
    public class EfPostRepository : EfEntityRepository<Post, EFContext>, IPostRepository
    {
        public ViewPost GetPostList(int postId)
        {
            using (var context = new EFContext())
            {
                return context.Set<ViewPost>().FirstOrDefault(x => x.PostId == postId && x.Status == true);
            }
        }

        public List<ViewPost> GetPostsList()
        {
            using (var context = new EFContext())
            {
                var result = context.ViewPosts.ToList();
                return result;
            }
        }

        public List<ViewPost> GetPostsList(int page,int pageSize)
        {
            using (var context = new EFContext())
            {
                var result = context.ViewPosts.Skip(page * pageSize).Take(pageSize).ToList();
                return result;
            }
        }

    }
}
